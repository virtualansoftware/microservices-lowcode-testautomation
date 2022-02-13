package io.virtualan.kafka;

public abstract class Adapter {
    public void close() {}
    public void configure(java.util.Map<String, ?> configs, boolean isKey) {}
}
