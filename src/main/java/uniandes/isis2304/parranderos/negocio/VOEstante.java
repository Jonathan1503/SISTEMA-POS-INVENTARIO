package uniandes.isis2304.parranderos.negocio;

public interface VOEstante {
	
	   public long getId();
	   public long getIdsucursal();
	   public double getVolumenMax();
	   public double getPesoMax();
	   public long getCategoriaAlmac();
	   
	   public long getNivelAbastecimiento();
	   @Override
		public String toString();

}
