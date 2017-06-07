package xin.fallen.ProjUsedVeh.annotation;

import java.lang.annotation.*;

/**
 * Author: PicUpload
 * Date: 2017/3/23
 * Time: 23:28
 * Usage:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Alias {
    String value() default "";
}