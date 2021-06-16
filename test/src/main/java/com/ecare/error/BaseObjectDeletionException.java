package com.ecare.error;

public final class BaseObjectDeletionException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public BaseObjectDeletionException() {
        super();
    }

    public BaseObjectDeletionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BaseObjectDeletionException(final String message) {
        super(message);
    }

    public BaseObjectDeletionException(final Throwable cause) {
        super(cause);
    }

}
