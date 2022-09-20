package com.enderio.core.common.util.vec;

import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class Vector4d {

    public static Vector4d IDENTITY = new Vector4d(0, 0, 0, 0);

    private double x;
    private double y;
    private double z;
    private double w;

    public Vector4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4d copy() {
        return new Vector4d(x, y, z, w);
    }

    public Vector4d interpolate(Vector4d destination, double factor) {
        x = (1 - factor) * x + factor * destination.x;
        y = (1 - factor) * y + factor * destination.y;
        z = (1 - factor) * z + factor * destination.z;
        w = (1 - factor) * w + factor * destination.w;
        return this;
    }

    public Vector4d add(Vector4d other) {
        return add(other.x, other.y, other.z, other.w);
    }

    public Vector4d add(double x, double y, double z, double w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    public Vector4d sub(Vector4d other) {
        return sub(other.x, other.y, other.z, other.w);
    }

    public Vector4d sub(double x, double y, double z, double w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    public Vector4d negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    public Vector4d scale(double scale) {
        x *= scale;
        y *= scale;
        z *= scale;
        w *= scale;
        return this;
    }

    public Vector4d normalize() {
        return scale(1.0f / Math.sqrt(x * x + y * y + z * z + w * w));
    }

    public double dot(Vector4d other) {
        return x * other.x + y * other.y + z * other.z + w * other.w;
    }

    public double lengthSqr() {
        return x * x + y * y + z * z + w * w;
    }

    public double length() {
        return Math.sqrt(lengthSqr());
    }

    public Vec3 toVec3() {
        return new Vec3(x, y, z);
    }

    public Vector4d withX(double x) {
        return new Vector4d(x, y, z, w);
    }

    public Vector4d withY(double y) {
        return new Vector4d(x, y, z, w);
    }

    public Vector4d withZ(double z) {
        return new Vector4d(x, y, z, w);
    }

    public Vector4d withW(double w) {
        return new Vector4d(x, y, z, w);
    }

    public Vector4d set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public double w() {
        return w;
    }

    @Override
    public String toString() {
        return "Vector4d{" + "x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (o instanceof Vector4d other) {
            return x == other.x && y == other.y && z == other.z && w == other.w;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }
}
