package app.mumu.zhaosql.exception;

public class NotTableException extends BadRequestException {

    public NotTableException() {
        super("没有这个表");
    }

}
