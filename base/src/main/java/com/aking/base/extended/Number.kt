package com.aking.base.extended

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by Rick on 2023-11-16  18:12.<p>
 *
 * Description: Number类拓展
 */

/**
 * dp单位
 */
val Number.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )