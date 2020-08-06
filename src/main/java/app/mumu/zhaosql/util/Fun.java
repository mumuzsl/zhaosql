package app.mumu.zhaosql.util;

import app.mumu.zhaosql.model.support.BaseResponse;

public interface Fun<T, J, R> {

    R fun(T t, J j);
}
