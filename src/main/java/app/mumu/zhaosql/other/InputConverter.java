package app.mumu.zhaosql.other;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;

public interface InputConverter<DOMAIN> {

    default DOMAIN convertTo(String json) {
        return JSONObject.parseObject(json, (Type) this.getClass());
    }

}
