// Spline.java
//   Functions for setting up and evaluating a cubic interpolatory spline.
//   AUTHORS:   Lawrence Shampine, Richard Allen, Steven Pruess  for 
//              the text  Fundamentals of Numerical Computing
//   DATE:      February 27, 1996
//              minimal change convertion to Java August 11, 2003
// The file Spline.java is adapted from various published Fortran and
// pseudo code versions and is open source,
// covered by the Free Software Foundation license in that file.
// Avaliable at: http://www.csee.umbc.edu/~squire/download/Spline.java
package sugar.control.utils;

import java.text.*;

public class Spline
{
  int n, last_interval;
  double x[], f[], b[], c[], d[];
  boolean uniform, debug;
  
  public Spline(double xx[], double ff[])
  {
    // Calculate coefficients defining a smooth cubic interpolatory spline.
    //
    // Input parameters:
    //   xx  = vector of values of the independent variable ordered
    //         so that  x[i] < x[i+1]  for all i.
    //   ff  = vector of values of the dependent variable.
    // class values constructed:
    //   n   = number of data points.
    //   b   = vector of S'(x[i]) values.
    //   c   = vector of S"(x[i])/2 values.
    //   d   = vector of S'''(x[i]+)/6 values (i < n).
    //   x = xx
    //   f = ff
    // Local variables:   
    double fp1, fpn, h, p;
    final double zero = 0.0, two = 2.0, three = 3.0;
    DecimalFormat f1 = new DecimalFormat("00.00000");
    boolean sorted = true;
    
    uniform = true;
    debug = false;
    last_interval = 0;
    n = xx.length;
    if(n<=3)
    {
      throw new IllegalArgumentException("Musisz podać conajmniej 3 punkty.");
    }
    if(n!=ff.length)
    {
      throw new IllegalArgumentException("Tablica argumentów musi miec tę samą dlugośc co tablica wartości.");
    }
    x = new double[n];
    f = new double[n];
    b = new double[n];
    c = new double[n];
    d = new double[n];
    for(int i=0; i<n; i++)
    {
      x[i] = xx[i];
      f[i] = ff[i];
      if(debug) System.out.println("Spline data x["+i+"]="+x[i]+", f[]="+f[i]);
      if(i>=1 && x[i]<x[i-1]) throw new IllegalArgumentException("Wartości argumentów musza być posortowane.");
    }
    
    // Calculate coefficients for the tri-diagonal system: store
    // sub-diagonal in b, diagonal in d, difference quotient in c. 
    b[0] = x[1]-x[0];
    c[0] = (f[1]-f[0])/b[0];
    if(n==2)
    {
       b[0] = c[0];
       c[0] = zero;
       d[0] = zero;
       b[1] = b[0];
       c[1] = zero;
       return;
    }
    d[0] = two*b[0];
    for(int i=1; i<n-1; i++)
    {
       b[i] = x[i+1]-x[i];
       if(Math.abs(b[i]-b[0])/b[0]>1.0E-5) uniform = false;
       c[i] = (f[i+1]-f[i])/b[i];
       d[i] = two*(b[i]+b[i-1]);
    }
    d[n-1] = two*b[n-2];

    // Calculate estimates for the end slopes.  Use polynomials
    // interpolating data nearest the end. 
    fp1 = c[0]-b[0]*(c[1]-c[0])/(b[0]+b[1]);
    if(n>3) fp1 = fp1+b[0]*((b[0]+b[1])*(c[2]-c[1])/
                  (b[1]+b[2])-c[1]+c[0])/(x[3]-x[0]);
    fpn = c[n-2]+b[n-2]*(c[n-2]-c[n-3])/(b[n-3]+b[n-2]);
    if(n>3) fpn = fpn+b[n-2]*(c[n-2]-c[n-3]-(b[n-3]+
                  b[n-2])*(c[n-3]-c[n-4])/(b[n-3]+b[n-4]))/(x[n-1]-x[n-4]);

    // Calculate the right-hand-side and store it in c. 
    c[n-1] = three*(fpn-c[n-2]);
    for(int i=n-2; i>0; i--)
       c[i] = three*(c[i]-c[i-1]);
    c[0] = three*(c[0]-fp1);

    // Solve the tridiagonal system. 
    for(int k=1; k<n; k++)
    {
       p = b[k-1]/d[k-1];
       d[k] = d[k]-p*b[k-1];
       c[k] = c[k]-p*c[k-1];
    }
    c[n-1] = c[n-1]/d[n-1];
    for(int k=n-2; k>=0; k--)
       c[k] = (c[k]-b[k]*c[k+1])/d[k];

    // Calculate the coefficients defining the spline. 
    h = x[1]-x[0];
    for(int i=0; i<n-1; i++)
    {
       h = x[i+1]-x[i];
       d[i] = (c[i+1]-c[i])/(three*h);
       b[i] = (f[i+1]-f[i])/h-h*(c[i]+h*d[i]);
    }
    b[n-1] = b[n-2]+h*(two*c[n-2]+h*three*d[n-2]);
    if(debug) System.out.println("spline coefficients");
    for(int i=0; i<n; i++)
    {
      if(debug) System.out.println("i="+i+", b[i]="+f1.format(b[i])+", c[i]="+
                         f1.format(c[i])+", d[i]="+f1.format(d[i]));
    }
  } // end Spline 


  public double lastValue() {
    return x[n-1];
  }
  
  public double val(double t)
  {
    // Evaluate the spline s at t using coefficients from Spline constructor
    //
    // Input parameters
    //   class variables
    //   t = point where spline is to be evaluated.
    // Output:
    //   s = value of spline at t.
    // Local variables:    
    double dt, s;
    int interval; // index such that t>=x[interval] and t<x[interval+1]

    if(n<=1)
    {
      throw new RuntimeException("Podaj conajmniej 2 wartości.");
    }
    // Search for correct interval for t. 
    interval = last_interval; // heuristic
    if(t<x[0] || t>x[n-1])
    {
      throw new IllegalArgumentException("Wartość jest poza zakresem");
    }
    if(t>x[n-2])
       interval = n-2;
    else if (t >= x[last_interval])
       for(int j=last_interval; j<n&&t>=x[j]; j++) interval = j;
    else
       for(int j=last_interval; t<x[j]; j--) interval = j-1;
    last_interval = interval; // class variable for next call 
    if(debug) System.out.println("interval="+interval+", x[interval]="+
                                 x[interval]+", t="+t);
    // Evaluate cubic polynomial on [x[interval] , x[interval+1]]. 
    dt = t-x[interval];
    s = f[interval]+dt*(b[interval]+dt*(c[interval]+dt*d[interval]));
    return s;
  } // end spline_value 
  
  public double integrate()
  {
    double suma, sumb, sumc, sumd;
    double dx, t;
    if(n<=3) 
    {
      throw new RuntimeException("Podaj conajmniej 2 wartości.");
    }
    if(!uniform)
    {
      if(debug) System.out.println("non uniform spacing integration");
      t = 0.0;
      for(int i=0; i<n-1; i++)
      {
        dx = x[i+1]-x[i];
        t = t + (f[i]+(b[i]/2.0+(c[i]/3.0+dx*d[i]/4.0)*dx)*dx)*dx;
      }
      return t;
    }
    // compute uniform integral of spline fit 
    suma = 0.0;
    sumb = 0.0;
    sumc = 0.0;
    sumd = 0.0;
    for(int i=0; i<n; i++)
    {
      suma = suma + d[i]; 
      sumb = sumb + c[i]; 
      sumc = sumc + b[i]; 
      sumd = sumd + f[i]; 
    }
    dx = x[1]-x[0]; // assumes equally spaced points 
    t = (sumd+(sumc/2.0+(sumb/3.0+dx*suma/4.0)*dx)*dx)*dx;
    if(debug) System.out.println("suma="+suma+", sumb="+sumb+",\n sumc="+
                                  sumc+", sumd="+sumd);
    return t;
  } // end integral 

 
} // end class Spline