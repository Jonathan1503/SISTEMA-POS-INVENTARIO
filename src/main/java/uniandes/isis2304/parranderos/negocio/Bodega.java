package uniandes.isis2304.parranderos.negocio;

public class Bodega implements VOBodega {
	
   private long id;
   private long idsucursal;
   private double volumenMax;
   private double pesoMax;
   private long categoriaAlmac;
   
   
   public Bodega() {
		this.id = 0;
		this.idsucursal = 0;
		this.volumenMax =0 ;
		this.pesoMax = 0;
		this.categoriaAlmac = 0;
		
	}
       
	public Bodega(long id, long idsucursal, double volumenMax, double pesoMax, long categoriaAlmac) {
		this.id = id;
		this.idsucursal = idsucursal;
		this.volumenMax = volumenMax;
		this.pesoMax = pesoMax;
		this.categoriaAlmac = categoriaAlmac;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdsucursal() {
		return idsucursal;
	}

	public void setIdsucursal(long idsucursal) {
		this.idsucursal = idsucursal;
	}

	public double getVolumenMax() {
		return volumenMax;
	}

	public void setVolumenMax(double volumenMax) {
		this.volumenMax = volumenMax;
	}

	public double getPesoMax() {
		return pesoMax;
	}

	public void setPesoMax(double pesoMax) {
		this.pesoMax = pesoMax;
	}

	public long getCategoriaAlmac() {
		return categoriaAlmac;
	}

	public void setCategoriaAlmac(long categoriaAlmac) {
		this.categoriaAlmac = categoriaAlmac;
	}
	
	

	public String toString() {
		return "Bodega [id="+id+", idsucursal="+idsucursal+", volumenMax="+volumenMax+", pesoMax"+pesoMax+", categoriaAlmac="+
	    categoriaAlmac+"]";    
	}
       
       

}
