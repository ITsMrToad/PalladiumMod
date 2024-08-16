package com.mr_toad.palladium.common.util;

import com.google.common.collect.Iterators;
import net.minecraft.MethodsReturnNonnullByDefault;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ImmutableArrayList<T> implements List<T> {

    private final T[] array;

    @SuppressWarnings("unchecked")
    public ImmutableArrayList(List<T> list) {
        this(list.toArray((T[]) new Object[0]));
    }

    public ImmutableArrayList(T[] array) {
        this.array = array;
    }

    @Override
    public int size() {
        return this.array.length;
    }

    @Override
    public boolean isEmpty() {
        return this.array.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        return ArrayUtils.contains(this.array, o);
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.forArray(this.array);
    }

    @Override
    public Object[] toArray() {
        return this.array.clone();
    }

    @Override
    @SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
    public <T1> T1[] toArray(T1[] dst) {
        T[] src = this.array;

        if (dst.length < src.length) {
            return (T1[]) Arrays.copyOf(src, src.length, dst.getClass());
        }

        System.arraycopy(src, 0, dst, 0, src.length);

        if (dst.length > src.length) {
            dst[src.length] = null;
        }

        return dst;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!ArrayUtils.contains(this.array, o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T get(int index) {
        return this.array[index];
    }

    @Override
    public int indexOf(Object o) {
        return ArrayUtils.indexOf(this.array, o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return ArrayUtils.lastIndexOf(this.array, o);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("List is immutable!");
    }
}
