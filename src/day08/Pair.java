package day08;

import day03.Position;

import java.util.Objects;

public class Pair<T, U> {
    T left;
    U right;

    public Pair(T left, U right) {
        this.left = left;
        this.right = right;
    }

    public Pair() {
        left = null;
        right = null;
    }

    public T getLeft() {
        return left;
    }

    public U getRight() {
        return right;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public void setRight(U right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "left='" + left + '\'' +
                ", right='" + right + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) obj;
        return left.equals(pair.left) && right.equals(pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}