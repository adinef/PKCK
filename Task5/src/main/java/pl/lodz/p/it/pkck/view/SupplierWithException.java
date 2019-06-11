package pl.lodz.p.it.pkck.view;

@FunctionalInterface
public interface SupplierWithException<S> {
    S get();
}
