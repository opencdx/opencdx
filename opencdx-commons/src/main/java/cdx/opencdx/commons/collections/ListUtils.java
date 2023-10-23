/*
 * Copyright 2023 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.commons.collections;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Utility Class for working with Java Lists.
 * <p>
 * Public Domain open source code, taken from
 * <a href="https://github.com/Ghost-Programmer/mymiller_extensions/blob/master/src/main/java/name/mymiller/utils/ListUtils.java">...</a>
 * added to this project by original author.
 * </p>
 *
 * @author jmiller Provide a set of utilities to use on lists.
 */
public class ListUtils {

    private ListUtils() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Convert a Map of K,V to a List of K
     *
     * @param map Map to Convert
     * @param <K> Type for K
     * @param <V> Type for V
     * @return List of K
     */
    public static <K, V> List<K> mapKeyToList(Map<K, V> map) {
        return new ArrayList<>(map.keySet());
    }

    /**
     * Convert and Iterator of E to a List of E
     *
     * @param iterator Iterator of E to convert
     * @param <E>      Type to iterate over
     * @return List of E converted.
     */
    public static <E> List<E> iterate(Iterator<E> iterator) {
        Iterable<E> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false).toList();
    }

    /**
     * Method to convert a Set to a List.
     *
     * @param set Set to convert to List
     * @param <E> Type of element in list
     * @return List
     */
    public static <E> List<E> toList(Set<E> set) {
        return new ArrayList<>(set);
    }

    /**
     * Convert a Map of K,V to a List of V
     *
     * @param map Map to Convert
     * @param <K> Type for K
     * @param <V> Type for V
     * @return List of V
     */
    public static <K, V> List<V> mapValuesToList(Map<K, V> map) {
        return new ArrayList<>(map.values());
    }

    /**
     * Create a list of the unique elements in both Lists.
     *
     * @param <E> Type used in lists, which must have overridden equals();
     * @param a   List A
     * @param b   List B
     * @return List that contains the unique elements not shared between lists.
     */
    public static <E> List<E> unique(List<E> a, List<E> b) {
        final List<E> unionList = ListUtils.union(a, b);
        unionList.removeAll(ListUtils.intersection(a, b));
        return unionList.stream().distinct().toList();
    }

    /**
     * Join two lists together to form a new List
     *
     * @param <E> Type used in lists, which must have overridden equals();
     * @param a   List A
     * @param b   List B
     * @return List that contains the contents of both Lists.
     */
    public static <E> List<E> union(List<E> a, List<E> b) {
        final List<E> unionList = new ArrayList<>(ListUtils.safe(a));
        unionList.addAll(ListUtils.safe(b));
        return new ArrayList<>(ListUtils.safe(unionList.stream().distinct().toList()));
    }

    /**
     * Find the common elements between two Lists
     *
     * @param <E> Type used in lists, which must have overridden equals();
     * @param a   List A
     * @param b   List B
     * @return List that contains the common elements between the Lists.
     */
    public static <E> List<E> intersection(List<E> a, List<E> b) {
        b = ListUtils.safe(b);
        return ListUtils.safe(a).stream().distinct().filter(b::contains).toList();
    }

    /**
     * Guarantees a list will be safe to use, even if null.
     *
     * @param list List to check if null
     * @param <E>  List Type
     * @return List, or an empty list of E.
     */
    public static <E> List<E> safe(List<E> list) {
        if (list != null) {
            return list;
        }

        return Collections.emptyList();
    }

    /**
     * Returns boolean indicating if the List is not empty
     *
     * @param list List to check
     * @param <E>  List type
     * @return boolean indicating if List is not empty
     */
    public static <E> boolean notEmpty(List<E> list) {
        return !ListUtils.isEmpty(list);
    }

    /**
     * Returns boolean indicating if the List is empty
     *
     * @param list List to check
     * @param <E>  List type
     * @return boolean indicating if LIst is empty
     */
    public static <E> boolean isEmpty(List<E> list) {
        if (list != null) {
            return list.isEmpty();
        }

        return true;
    }

    /**
     * Returns the number of elements in the list.  Checks for Null.
     *
     * @param list List to check
     * @param <E>  List Type
     * @return int indicating the number of elements in the list or 0 if null.
     */
    public static <E> int size(List<E> list) {
        if (list == null) {
            return 0;
        }

        return list.size();
    }

    /**
     * Performs pagination on the List,  returning the indicated page of elements, or an empty ist.
     *
     * @param list     List to paginate
     * @param pageSize Number of elements per page
     * @param page     The Page to return
     * @param <E>      Type of Elements
     * @return Empty list if no matching page, or List containing the page's elements.
     */
    public static <E> List<E> page(List<E> list, int pageSize, int page) {
        List<E> safe = ListUtils.safe(list);

        if (pageSize > 0 && page >= 0 && !safe.isEmpty()) {
            int startIndex = pageSize * page;
            int endIndex = startIndex + pageSize;
            if (startIndex > safe.size()) {
                return Collections.emptyList();
            }

            if (endIndex > safe.size()) {
                endIndex = safe.size();
            }

            return safe.subList(startIndex, endIndex);
        }

        return Collections.emptyList();
    }

    /**
     * Determines the total number of pages of data from a list given a page size
     *
     * @param list     List to use for pagination
     * @param pageSize Page Size
     * @param <E>      Type of element in list
     * @return number of pages in the list.
     */
    public static <E> int pages(List<E> list, int pageSize) {
        if (ListUtils.isEmpty(list)) {
            return 0;
        }
        return (list.size() / pageSize) + 1;
    }

    /**
     * Filter out elements based on Predicate. Simplified method to stream filter.
     *
     * @param list      List to filter
     * @param predicate Predicate to indicate whether to remove of keep elements in the list
     * @param <E>       Type of Elements
     * @return List containing the elements after the filter.
     */
    public static <E> List<E> filter(List<E> list, Predicate<E> predicate) {
        return ListUtils.safe(list).parallelStream().filter(predicate).toList();
    }

    /**
     * Sets a value on a field for all objects in a list.
     *
     * @param list   List to set value on
     * @param value  Value to set
     * @param setter Setter reference to set value on
     * @param <E>    Type of item in list
     * @param <R>    Type of Value to set
     * @return List with value set or an empty list.
     */
    @SuppressWarnings("java:S3864")
    public static <E, R> List<E> setValue(List<E> list, R value, BiConsumer<E, R> setter) {
        return ListUtils.safe(list).parallelStream()
                .peek(item -> setter.accept(item, value))
                .toList();
    }

    /**
     * Returns a list of values from the field of the list
     *
     * @param list   List to retrieve values from
     * @param getter Getter method to retrieve value
     * @param <E>    Type of object in LIst
     * @param <R>    Type of Value to get
     * @return List of type R from all objects in list.
     */
    public static <E, R> List<R> getValue(List<E> list, Function<E, R> getter) {
        return ListUtils.safe(list).parallelStream().map(getter).toList();
    }

    /**
     * Convert a List to a map
     *
     * @param list   List to convert to Map
     * @param getter Getter to use to get Key
     * @param <E>    Type of Value
     * @param <R>    Type of Key
     * @return Map of elements E, based on Key R.
     */
    public static <E, R> Map<R, E> toMap(List<E> list, Function<? super E, ? extends R> getter) {
        return ListUtils.safe(list).parallelStream().collect(Collectors.toMap(getter, Function.identity()));
    }
}
