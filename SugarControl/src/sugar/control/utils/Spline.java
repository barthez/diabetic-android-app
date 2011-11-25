package sugar.control.utils;
/**
 *
 * @author Bartłomiej Bułat <bartek.bulat at gmail.com>
 */
public class Spline {

  double x[];
  double y[];
  double dv[];
  int n = 0;

  public Spline(double[] _x, double _y[]) {
    if (_x.length != _y.length) {
      throw new IllegalArgumentException("Długości tablicy argumentów musi być równa długości tablicy wartości.");
    }

    if (_x.length < 3) {
      throw new IllegalArgumentException("Muszą być podane conajmniej 2 wartości.");
    }

    n = _x.length;

    x = new double[n];
    y = new double[n];
    dv = new double[n];

    for (int i = 0; i < _x.length; i++) {
      this.x[i] = _x[i];
      this.y[i] = _y[i];
    }

    evaluateDV();

  }

  public double val(double _x) {
    if (_x < x[0] || _x > x[n - 1]) {
      throw new IndexOutOfBoundsException("Wartość spoza zakersu");
    }
    int it = 0;
    for (int i = 1; i < n; ++i) {
      if (_x < x[i]) {
        it = i - 1;
        break;
      }
    }
    double h = x[it] - x[it+1];
    double dxi = _x - x[it+1];
    double dx = _x - x[it];
    
    return (dxi*dxi*dxi/h - dxi*h) * (dv[it]/6) - (dx*dx*dx/h - dx*h)*(dv[it+1]/6) + y[it]*dxi/h - y[it+1]*dx/h;
  }

  private void evaluateDV() {
    double c[] = new double[n];
    double d[] = new double[n];
    double e[] = new double[n];
    double k[] = new double[n];

    for (int i = 0; i < n - 1; ++i) {
      if (i < n - 2) {
        c[i] = x[i] - x[i + 1];
      } else {
        c[i] = 0;
      }

      if (i == 0) {
        d[i] = 1;
        e[i] = 0;
        k[i] = 0;
      } else {
        d[i] = 2 * (x[i - 1] - x[i + 1]);
        e[i] = x[i] - x[i + 1];
        k[i] = 6.0 * (y[i - 1] - y[i]) / (x[i - 1] - x[i]) + 6.0 * (y[i] - y[i + 1]) / (x[i] - x[i + 1]);
      }
    }
    d[n - 1] = 1;

    // Dekompozycja LU
    for (int i = 1; i < n; ++i) {
      double lambda = c[i - 1] / d[i - 1];
      d[i] = d[i] - lambda * e[i - 1];
      c[i - 1] = lambda;
    }

    // Rozwiązanie LU
    for (int i = 1; i < n - 1; ++i) {
      k[i] = k[i] - c[i - 1] * k[i - 1];
    }

    dv[n - 1] = k[n - 1] / d[n - 1];
    for (int i = n - 2; i >= 0; --i) {
      dv[i] = (k[i] - e[i] * k[i + 1]) / d[i];
    }



  }
}
