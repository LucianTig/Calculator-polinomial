package com.calpolinom.calculatorpolinoame;

import java.util.ArrayList;


public class Polinom {
    private ArrayList<Monom> pol;
    private int gradMaxim=0;

    public Polinom() {
        pol=new ArrayList<Monom>();
    }

    public void conPol(Monom a) {
        pol.add(a);
        if(a.getPutere()>gradMaxim)
            gradMaxim=a.getPutere();
    }

    public void derivare() {
        Monom as=null;
        for(Monom a:pol)
            if(a.getPutere()==0)
                as=a;
            else
            {
                a.setArg(a.getArg()*a.getPutere());
                a.setPutere(a.getPutere()-1);
            }
        pol.remove(as);
    }

    public void integrare() {
        for(Monom a:pol) {
            if(a.getPutere()==0)
                a.setPutere(a.getPutere()+1);
            else {

                a.setArg(a.getArg()/(a.getPutere()+1));
                a.setPutere(a.getPutere()+1);
            }
        }
    }

    public Polinom adunare(Polinom p1) {
        Polinom pol1=new Polinom();

        for(Monom a:this.pol)
            pol1.conPol(a);
        for(Monom a:p1.getLista())
            pol1.conPol(a);
        pol1=this.reducereTerm(pol1);
        return pol1;
    }

    public Polinom scadere(Polinom p1) {
        for(Monom a:p1.pol)
            a.setArg(-a.getArg());
        p1=this.adunare(p1);
        return p1;
    }

    public Polinom inmultire(Polinom p1) {
        Polinom pol1=new Polinom();
        for(Monom a:p1.getLista())
            for(Monom b:pol) {
                pol1.conPol(new Monom(a.getArg()*b.getArg(),a.getPutere()+b.getPutere()));
            }
        pol1=this.reducereTerm(pol1);
        return pol1;
    }

    public ArrayList<Polinom> impartire(Polinom p1) {
        ArrayList<Polinom> listaQR=new ArrayList<>();
        Polinom r=new Polinom();
        p1=this.reducereTerm(p1);     //aranjam termenii in ordine descrescatoare a puterilor
        this.pol=(this.reducereTerm(this)).pol; // aranjam termenii

        for(Monom a:this.pol)
            r.conPol(a);
        Polinom q=new Polinom();

        if(p1.pol.size()==0) {
            try {
                throw new Exception("Nu se poate efectua impartire la 0!!");
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            while(r.pol.size()!=0 && r.pol.get(0).getPutere()>=p1.pol.get(0).getPutere()) {
                Monom t=new Monom(r.pol.get(0).getArg()/p1.pol.get(0).getArg(),r.pol.get(0).getPutere()-p1.pol.get(0).getPutere());
                Polinom t1=new Polinom();
                t1.conPol(t);
                q.conPol(t);
                r=r.scadere(t1.inmultire(p1));
            }
            System.out.println("restul:"+r);
        }
        listaQR.add(0,q);
        listaQR.add(1,r);
        return listaQR;

    }

    public Polinom reducereTerm(Polinom pol2) {
        int k=0;
        Polinom p1=new Polinom();
        for(int i=pol2.gradMaxim;i>=0;i--) {
            Monom mon=new Monom(0,i);
            p1.conPol(mon);
            for(Monom a:pol2.getLista()) {
                if(a.getPutere()==i) {
                    p1.pol.get(k).setArg(p1.pol.get(k).getArg()+a.getArg());
                }
            }
            k++;
        }

        Polinom p2=new Polinom();
        for(Monom a:p1.pol)
            if(a.getArg()!=0)
                p2.conPol(a);

        return p2;
    }

	/*public int setGradMaxim(Polinom p1) {
		int max=0;
		for(Monom a:p1.getLista())
			if(max<a.getPutere())
				max=a.getPutere();
		return max;
	}*/

    public String toString() {
        String a="";
        for(Monom b:pol) {
            if(b.getArg()>=0) {
                if(b.getPutere()!=0 && b.getPutere()!=1)
                    a=a+"+"+b.getArg()+"x^"+b.getPutere();
                else
                if(b.getPutere()==0)
                    a=a+"+"+b.getArg();
                else
                    a=a+"+"+b.getArg()+"x";
            }
            else {
                //a=a+b.getArg()+"x^"+b.getPutere();
                if(b.getPutere()!=0 && b.getPutere()!=1)
                    a=a+b.getArg()+"x^"+b.getPutere();
                else
                if(b.getPutere()==0)
                    a=a+b.getArg();
                else
                    a=a+b.getArg()+"x";
            }

        }
        return a;
    }

    public ArrayList<Monom> getLista(){
        return pol;
    }

}

