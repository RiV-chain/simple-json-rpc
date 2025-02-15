package com.github.arteam.simplejsonrpc.client.builder;

import com.github.arteam.simplejsonrpc.client.JsonRpcId;
import com.github.arteam.simplejsonrpc.client.JsonRpcParams;
import com.github.arteam.simplejsonrpc.client.ParamsType;
import com.github.arteam.simplejsonrpc.client.generator.AtomicLongIdGenerator;
import com.github.arteam.simplejsonrpc.client.generator.IdGenerator;
import com.github.arteam.simplejsonrpc.client.metadata.ClassMetadata;
import com.github.arteam.simplejsonrpc.client.metadata.MethodMetadata;
import com.github.arteam.simplejsonrpc.client.metadata.ParameterMetadata;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.riv.annotations.Contract;
import org.riv.annotations.External;
import org.riv.annotations.OptionalParameter;
import org.riv.annotations.Parameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Date: 11/17/14
 * Time: 8:04 PM
 * Utility class for gathering meta-information about client proxies through reflection
 */
class Reflections {

    private Reflections() {
    }

    /**
     * Gets remote service interface metadata
     *
     * @param clazz an interface for representing a remote service
     * @return class metadata
     */
    @NotNull
    public static ClassMetadata getClassMetadata(@NotNull Class<?> clazz) {
        Map<Method, MethodMetadata> methodsMetadata = new HashMap<Method, MethodMetadata>(32);
        Class<?> searchClass = clazz;
        while (searchClass != null) {
            Contract rpcServiceAnn = getAnnotation(searchClass.getAnnotations(), Contract.class);
            if (rpcServiceAnn == null) {
                throw new IllegalStateException("Class '" + clazz.getCanonicalName() +
                        "' is not annotated as @JsonRpcService");
            }
            Method[] methods = searchClass.getMethods();
            for (Method method : methods) {
                Annotation[] methodAnnotations = method.getDeclaredAnnotations();
                External rpcMethodAnn = getAnnotation(methodAnnotations, External.class);
                if (rpcMethodAnn == null) {
                    throw new IllegalStateException("Method '" + method.getName() + "' is not annotated as @JsonRpcMethod");
                }

                // LinkedHashMap is needed to support method parameter ordering
                Map<String, ParameterMetadata> paramsMetadata = new LinkedHashMap<String, ParameterMetadata>(8);
                Annotation[][] parametersAnnotations = method.getParameterAnnotations();
                for (int i = 0; i < parametersAnnotations.length; i++) {
                    Annotation[] parametersAnnotation = parametersAnnotations[i];
                    // Check that it's a JSON-RPC param
                    Parameter rpcParamAnn = getAnnotation(parametersAnnotation, Parameter.class);
                    if (rpcParamAnn == null) {
                        throw new IllegalStateException("Parameter with index=" + i + " of method '" + method.getName() +
                                "' is not annotated with @JsonRpcParam");
                    }
                    // Check that's a param could be an optional
                    OptionalParameter optionalAnn = getAnnotation(parametersAnnotation, OptionalParameter.class);
                    ParameterMetadata parameterMetadata = new ParameterMetadata(i, optionalAnn != null);
                    if (paramsMetadata.put(rpcParamAnn.value(), parameterMetadata) != null) {
                        throw new IllegalStateException("Two parameters of method '" + method.getName() + "' have the " +
                                "same name '" + rpcParamAnn.value() + "'");
                    }

                }
                String name = !rpcMethodAnn.value().isEmpty() ? rpcMethodAnn.value() : method.getName();
                ParamsType paramsType = getParamsType(methodAnnotations);
                methodsMetadata.put(method, new MethodMetadata(name, paramsType, paramsMetadata));
            }
            searchClass = searchClass.getSuperclass();
        }

        Annotation[] classAnnotations = clazz.getDeclaredAnnotations();
        IdGenerator<?> idGenerator = getIdGenerator(classAnnotations);
        ParamsType paramsType = getParamsType(classAnnotations);
        return new ClassMetadata(paramsType, idGenerator, methodsMetadata);
    }


    /**
     * Get an actual id generator
     */
    @NotNull
    private static IdGenerator<?> getIdGenerator(@NotNull Annotation[] classAnnotations) {
        JsonRpcId jsonRpcIdAnn = getAnnotation(classAnnotations, JsonRpcId.class);
        Class<? extends IdGenerator<?>> idGeneratorClazz = (jsonRpcIdAnn == null) ?
                AtomicLongIdGenerator.class : jsonRpcIdAnn.value();
        try {
            return idGeneratorClazz.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Unable instantiate id generator: " + idGeneratorClazz, e);
        }
    }

    @Nullable
    private static ParamsType getParamsType(@NotNull Annotation[] annotations) {
        JsonRpcParams rpcParamsAnn = getAnnotation(annotations, JsonRpcParams.class);
        return rpcParamsAnn != null ? rpcParamsAnn.value() : null;

    }

    @SuppressWarnings("unchecked")
    @Nullable
    private static <T extends Annotation> T getAnnotation(@Nullable Annotation[] annotations,
                                                          @NotNull Class<T> clazz) {
        if (annotations != null) {
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(clazz)) {
                    return (T) annotation;
                }
            }
        }
        return null;
    }
}
