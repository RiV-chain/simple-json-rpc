package com.github.arteam.simplejsonrpc.client.builder;

import com.github.arteam.simplejsonrpc.client.JsonRpcId;
import com.github.arteam.simplejsonrpc.client.JsonRpcParams;
import com.github.arteam.simplejsonrpc.client.ParamsType;
import com.github.arteam.simplejsonrpc.client.generator.CurrentTimeIdGenerator;
import com.github.arteam.simplejsonrpc.client.generator.IdGenerator;
import com.github.arteam.simplejsonrpc.client.metadata.ClassMetadata;
import com.github.arteam.simplejsonrpc.client.metadata.MethodMetadata;
import com.github.arteam.simplejsonrpc.client.metadata.ParameterMetadata;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcMethod;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcOptional;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcParam;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 11/17/14
 * Time: 8:04 PM
 *
 * @author Artem Prigoda
 */
class Reflections {

    private Reflections() {
    }

    @Nullable
    public static ClassMetadata getClassMetadata(@NotNull Class<?> clazz) {
        Annotation[] classAnnotations = clazz.getDeclaredAnnotations();
        JsonRpcService rpcServiceAnn = getAnnotation(classAnnotations, JsonRpcService.class);
        if (rpcServiceAnn == null) {
            throw new IllegalStateException("Class '" + clazz.getCanonicalName() +
                        "' is not annotated as @JsonRpcService");
        }

        Map<Method, MethodMetadata> methodsMetadata = new HashMap<Method, MethodMetadata>(32);
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotation[] methodAnnotations = method.getDeclaredAnnotations();
            JsonRpcMethod rpcMethodAnn = getAnnotation(methodAnnotations, JsonRpcMethod.class);
            if (rpcMethodAnn == null) {
                // Actually not an error, because every object has standard methods (equals, hashCode, etc...)
                continue;
            }
            Map<String, ParameterMetadata> paramsMetadata = new HashMap<String, ParameterMetadata>(8);
            Annotation[][] parametersAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parametersAnnotations.length; i++) {
                Annotation[] parametersAnnotation = parametersAnnotations[i];
                // Check that it's a JSON-RPC param
                JsonRpcParam rpcParamAnn = getAnnotation(parametersAnnotation, JsonRpcParam.class);
                if (rpcParamAnn == null) {
                    throw new IllegalStateException("Parameter with index=" + i + " of method '" + method.getName() +
                            "' is not annotated with @JsonRpcParam");
                }
                // Check that's a param could be an optional
                JsonRpcOptional optionalAnn = getAnnotation(parametersAnnotation, JsonRpcOptional.class);
                paramsMetadata.put(rpcParamAnn.value(), new ParameterMetadata(i, optionalAnn != null));
            }
            String name = !rpcMethodAnn.value().isEmpty() ? rpcMethodAnn.value() : method.getName();
            ParamsType paramsType = getParamsType(methodAnnotations);
            methodsMetadata.put(method, new MethodMetadata(name, paramsType, paramsMetadata));
        }

        IdGenerator<?> idGenerator = getIdGenerator(classAnnotations);
        ParamsType paramsType = getParamsType(classAnnotations);
        return new ClassMetadata(paramsType, idGenerator, methodsMetadata);
    }


    /**
     * Get actual id generator
     */
    @NotNull
    private static IdGenerator<?> getIdGenerator(@NotNull Annotation[] classAnnotations) {
        JsonRpcId jsonRpcIdAnn = getAnnotation(classAnnotations, JsonRpcId.class);
        // TODO change to AtomicLongIdGenerator as a default choice
        Class<? extends IdGenerator<?>> idGeneratorClazz = (jsonRpcIdAnn == null) ?
                CurrentTimeIdGenerator.class : jsonRpcIdAnn.value();
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