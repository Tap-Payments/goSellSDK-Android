package company.tap.gosellapi.internal.utils;

public final class CompoundFilter<T> implements Utils.List.Filter<T> {

    private java.util.List<Utils.List.Filter<T>> filters;

    @Override
    public boolean isIncluded(T object) {

        for (Utils.List.Filter<T> filter : filters) {

            if (!filter.isIncluded(object)) { return false; }
        }

        return true;
    }

    public CompoundFilter(java.util.List<Utils.List.Filter<T>> filters) {

        this.filters = filters;
    }
}