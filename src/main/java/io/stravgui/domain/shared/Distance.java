package io.stravgui.domain.shared;

public record Distance(double kilometers) {

    private static final double EPSILON = 1e-9;

    public Distance {
        if (kilometers < 0) {
            throw new IllegalArgumentException("La distance ne peut pas être négative : " + kilometers);
        }
    }

    public static Distance ofKm(double kilometers) {
        return new Distance(kilometers);
    }

    public static Distance ofMeters(double meters) {
        return new Distance(meters / 1000.0);
    }

    public static Distance zero() {
        return new Distance(0);
    }

    public Distance add(Distance other) {
        return new Distance(this.kilometers + other.kilometers);
    }

    public Distance subtract(Distance other) {
        double result = this.kilometers - other.kilometers;
        if (result < -EPSILON) {
            throw new IllegalArgumentException("Le résultat de la soustraction ne peut pas être négatif");
        }
        return new Distance(Math.max(result, 0));
    }

    public boolean isGreaterOrEqualTo(Distance other) {
        return this.kilometers >= other.kilometers - EPSILON;
    }

    public boolean isGreaterThan(Distance other) {
        return this.kilometers > other.kilometers + EPSILON;
    }

    public double inKm() {
        return kilometers;
    }

    public double inMeters() {
        return kilometers * 1000.0;
    }
}