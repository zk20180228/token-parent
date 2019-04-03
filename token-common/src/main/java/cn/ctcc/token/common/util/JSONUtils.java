package cn.ctcc.token.common.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Json处理工具类
 *
 * @author zk
 */
public class JSONUtils {

    /**
     * 空的 {@code JSON} 数据 - <code>"{}"</code>。
     */
    public static final String EMPTY_JSON = "{}";

    /**
     * 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。
     */
    public static final String EMPTY_JSON_ARRAY = "[]";

    /**
     * 默认的 {@code JSON} 日期/时间字段的格式化模式。
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /** 针对hibernate懒加载格式化json对象使用 */
    //private static final TypeAdapterFactory TYPE_ADAPTER=HibernateCascade.FACTORY;

    /**
     * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
     *
     * @param target                      目标对象。
     * @param targetType                  目标对象的类型。(目标对象为泛型的时候使用)
     * @param isSerializeNulls            是否序列化 {@code null} 值字段。
     * @param datePattern                 日期字段的格式化模式。
     * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, Type targetType,
                                boolean isSerializeNulls, String datePattern,
                                boolean excludesFieldsWithoutExpose) {
        if (target == null)
            return EMPTY_JSON;
        GsonBuilder builder = new GsonBuilder();
		/*if(TYPE_ADAPTER!=null){
			builder.registerTypeAdapterFactory(TYPE_ADAPTER);
		}*/
        if (isSerializeNulls)
            builder.serializeNulls();
        if (StringUtils.isBlank(datePattern))
            datePattern = DEFAULT_DATE_PATTERN;
        builder.setDateFormat(datePattern);
        if (excludesFieldsWithoutExpose) {
            builder.excludeFieldsWithoutExposeAnnotation();
        }
        return toJson(target, targetType, builder);
    }

    /**
     * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
     *
     * @param target                      目标对象。
     * @param isSerializeNulls            是否序列化 {@code null} 值字段。
     * @param datePattern                 日期字段的格式化模式。
     * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, boolean isSerializeNulls,
                                String datePattern, boolean excludesFieldsWithoutExpose) {
        return toJson(target, null, isSerializeNulls, datePattern,
                excludesFieldsWithoutExpose);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     * <ul>
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
     * </ul>
     *
     * @param target 要转换成 {@code JSON} 的目标对象。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target) {
        return toJson(target, null, false, null, false);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     * <ul>
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
     * </ul>
     *
     * @param target      要转换成 {@code JSON} 的目标对象。
     * @param datePattern 日期字段的格式化模式。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, String datePattern) {
        return toJson(target, null, false, datePattern, false);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     * <ul>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
     * </ul>
     *
     * @param target                      要转换成 {@code JSON} 的目标对象。
     * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target,
                                boolean excludesFieldsWithoutExpose) {
        return toJson(target, null, false, null, excludesFieldsWithoutExpose);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
     * <ul>
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
     * </ul>
     *
     * @param target     要转换成 {@code JSON} 的目标对象。
     * @param targetType 目标对象的类型。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, Type targetType) {
        return toJson(target, targetType, false, null, true);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
     * <ul>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
     * </ul>
     *
     * @param target                      要转换成 {@code JSON} 的目标对象。
     * @param targetType                  目标对象的类型。
     * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, Type targetType,
                                boolean excludesFieldsWithoutExpose) {
        return toJson(target, targetType, false, null,
                excludesFieldsWithoutExpose);
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
     *
     * @param <T>         要转换的目标类型。
     * @param json        给定的 {@code JSON} 字符串。
     * @param token       {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
     * @param datePattern 日期格式模式。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     * @since 1.0
     */
    public static <T> T fromJson(String json, TypeToken<T> token,
                                 String datePattern) throws Exception {
        if (StringUtils.isBlank(json)) {
            throw new RuntimeException("json 参数不能为空!");
        }
        json = cleanJsonBlankValue(json);
        GsonBuilder builder = new GsonBuilder();
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        Gson gson = builder.setDateFormat(datePattern).create();
        return gson.fromJson(json, token.getType());
        // LOGGER.error(json + " 无法转换为 " + token.getRawType().getName()
        // + " 对象!", ex);
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
     *
     * @param <T>   要转换的目标类型。
     * @param json  给定的 {@code JSON} 字符串。
     * @param token {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     * @throws Exception
     * @since 1.0
     */
    public static <T> T fromJson(String json, TypeToken<T> token)
            throws Exception {
        return fromJson(json, token, null);
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     *
     * @param <T>         要转换的目标类型。
     * @param json        给定的 {@code JSON} 字符串。
     * @param clazz       要转换的目标类。
     * @param datePattern 日期格式模式。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     * @since 1.0
     */
    public static <T> T fromJson(String json, Class<T> clazz, String datePattern)
            throws Exception {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        json = cleanJsonBlankValue(json);
        GsonBuilder builder = new GsonBuilder();
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        Gson gson = builder.setDateFormat(datePattern).create();
        return gson.fromJson(json, clazz);
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     *
     * @param <T>   要转换的目标类型。
     * @param json  给定的 {@code JSON} 字符串。
     * @param clazz 要转换的目标类。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     * @throws Exception
     * @since 1.0
     */
    public static <T> T fromJson(String json, Class<T> clazz) throws Exception {
        return fromJson(json, clazz, null);
    }

    /**
     * 将给定的目标对象根据{@code GsonBuilder} 所指定的条件参数转换成 {@code JSON} 格式的字符串。
     * <p/>
     * 该方法转换发生错误时，不会抛出任何异常。若发生错误时，{@code JavaBean} 对象返回 <code>"{}"</code>；
     * 集合或数组对象返回 <code>"[]"</code>。 其本基本类型，返回相应的基本值。
     *
     * @param target     目标对象。
     * @param targetType 目标对象的类型。
     * @param builder    可定制的{@code Gson} 构建器。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     * @since 1.1
     */
    private static String toJson(Object target, Type targetType,
                                 GsonBuilder builder) {
        if (target == null){
            return EMPTY_JSON;
        }
        Gson gson = null;
        if (builder == null) {
            gson = new Gson();
        } else {
            gson = builder.create();
        }
        String result = EMPTY_JSON;
        try {
            if (targetType == null) {
                result = gson.toJson(target);
            } else {
                result = gson.toJson(target, targetType);
            }
        } catch (Exception ex) {
            // LOGGER.warn("目标对象 " + target.getClass().getName()
            // + " 转换 JSON 字符串时，发生异常！", ex);
            if (target instanceof Collection<?>
                    || target instanceof Iterator<?>
                    || target instanceof Enumeration<?>
                    || target.getClass().isArray()) {
                result = EMPTY_JSON_ARRAY;
            }
        }
        return result;
    }

    /**
     * 将对象进行json格式化,只包含对象中(exposeArray的属性)
     *
     * @param target           目标对象。
     * @param isSerializeNulls 是否序列化 {@code null} 值字段。
     * @param datePattern      日期字段的格式化模式。
     * @param exposeArray      目标对象中的属性
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toExposeJson(Object target, boolean isSerializeNulls,
                                      String datePattern, String... exposeArray) {
        if (target == null)
            return EMPTY_JSON;
        GsonBuilder builder = new GsonBuilder();
		/*if(TYPE_ADAPTER!=null){
			builder.registerTypeAdapterFactory(TYPE_ADAPTER);
		}*/
        if (isSerializeNulls)
            builder.serializeNulls();
        if (StringUtils.isBlank(datePattern))
            datePattern = DEFAULT_DATE_PATTERN;
        builder.setDateFormat(datePattern);
        if (exposeArray != null) {
            final String[] skips = exposeArray;
            builder.setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    if (skips != null) {
                        for (String skip : skips) {
                            if (skip.equals(f.getName())) {
                                return false;
                            }
                        }
                    }
                    return true;
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            });
        }
        ;
        return toJson(target, null, builder);
    }

    /**
     * 将对象进行json格式化,排除对象中(exposeArray的属性)
     *
     * @param target           目标对象。
     * @param isSerializeNulls 是否序列化 {@code null} 值字段。
     * @param datePattern      日期字段的格式化模式。
     * @param exposeArray      目标对象中的属性
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toExclusionJson(Object target,
                                         boolean isSerializeNulls, String datePattern, String... exposeArray) {
        if (target == null)
            return EMPTY_JSON;
        GsonBuilder builder = new GsonBuilder();
		/*if(TYPE_ADAPTER!=null){
			builder.registerTypeAdapterFactory(TYPE_ADAPTER);
		}*/
        if (isSerializeNulls)
            builder.serializeNulls();
        if (StringUtils.isBlank(datePattern))
            datePattern = DEFAULT_DATE_PATTERN;
        builder.setDateFormat(datePattern);
        if (exposeArray != null) {
            final String[] skips = exposeArray;
            builder.setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    if (skips != null) {
                        for (String skip : skips) {
                            if (skip.equals(f.getName())) {
                                return true;
                            }
                        }
                    }
                    return false;
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            });
        }
        ;
        return toJson(target, null, builder);
    }

    /**
     * 清除json格式字符串中value值为" " 的json属性
     *
     * @param json 原始json  如  {"sdf":"","nan":"sdf","ses":"   ","sd12":"sdf","123":""}
     * @return {"nan":"sdf","sd12":"sdf"}
     */
    private static String cleanJsonBlankValue(String json) {
        return json = json.replaceAll("\"[\\w+]+\":\"\\s*\",", "")
                .replaceAll(",\"[\\w+]+\":\"\"\\s*", "")
                .replaceAll(",\"[\\w+]+\":\"\\s*\",", "");
    }
}
