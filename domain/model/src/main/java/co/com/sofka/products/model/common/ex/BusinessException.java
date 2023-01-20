package co.com.sofka.products.model.common.ex;

import java.util.function.Supplier;

public class BusinessException extends ApplicationException {

    public enum Type {
        BUY_WITH_NULL_FIELDS("There are null fields in the buy."),
        BUY_WITH_NULL_OR_EMPTY_PRODUCTS("There are null or empty products in the buy."),
        EXCEDS_PRODUCTS("the quantity of products in the purchase exceeds the minimum or maximum purchase limits for products");


        private final String message;

        public String getMessage() {
            return message;
        }

        public BusinessException build(String errorInfo) {
            return new BusinessException(this, errorInfo);
        }

        public Supplier<Throwable> defer() {
            return () -> new BusinessException(this, "");
        }

        Type(String message) {
            this.message = message;
        }

    }

    private final Type type;

    public BusinessException(Type type, String exceptionInfo) {
        super(type.message + " " + exceptionInfo);
        this.type = type;
    }

    @Override
    public String getCode() {
        return type.name();
    }

    public Type getType() {
        return type;
    }
}



