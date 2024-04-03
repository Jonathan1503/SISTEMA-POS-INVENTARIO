package uniandes.isis2304.parranderos.negocio;

public class Estante implements VOEstante{
	
	  private long id;
	   private long idsucursal;
	   
	   private long nivelAbastecimiento;
	   private double volumenMax;
	   private double pesoMax;
	   private long categoriaAlmac;
	   
	   public Estante() {
			this.id = 0;
			this.idsucursal = 0;
			this.volumenMax =0 ;
			this.pesoMax = 0;
			this.categoriaAlmac = 0;
			
			this.nivelAbastecimiento= 0;
			
		}
	       
		public Estante(long id, long idsucursal, long nivelAbastecimiento, double volumenMax, double pesoMax, long categoriaAlmac) {
			this.id = id;
			this.idsucursal = idsucursal;
			this.volumenMax = volumenMax;
			this.pesoMax = pesoMax;
			this.categoriaAlmac = categoriaAlmac;
			
			this.nivelAbastecimiento= nivelAbastecimiento;
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
		
		
		
		

		public long getNivelAbastecimiento() {
			return nivelAbastecimiento;
		}

		public void setNivelAbastecimiento(long nivelAbastecimiento) {
			this.nivelAbastecimiento = nivelAbastecimiento;
		}

		public String toString() {
			return "Estante [id="+id+", idsucursal="+idsucursal+", volumenMax="+volumenMax+", pesoMax"+pesoMax+", categoriaAlmac="+
		    categoriaAlmac+"]";    
		}

}
