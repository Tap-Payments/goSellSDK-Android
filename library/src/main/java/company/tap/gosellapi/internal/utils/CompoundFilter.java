package company.tap.gosellapi.internal.utils;

/**
 * The type Compound filter.
 *
 * @param <T> the type parameter
 */
public final class CompoundFilter<T> implements Utils.List.Filter<T> {

    private java.util.List<Utils.List.Filter<T>> filters;

    @Override
    public boolean isIncluded(T object) {

        for (Utils.List.Filter<T> filter : filters) {

            if (!filter.isIncluded(object)) { return false; }
        }

        return true;
    }

    /**
     * Instantiates a new Compound filter.
     *
     * @param filters the filters
     */
    public CompoundFilter(java.util.List<Utils.List.Filter<T>> filters) {

        this.filters = filters;
    }
}