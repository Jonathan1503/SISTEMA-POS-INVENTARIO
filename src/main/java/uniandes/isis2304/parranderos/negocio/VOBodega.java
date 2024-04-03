package uniandes.isis2304.parranderos.negocio;

public interface VOBodega {
	
	   public long getId();
	   public long getIdsucursal();
	   public double getVolumenMax();
	   public double getPesoMax();
	   public long getCategoriaAlmac();
	   
	   @Override
		public String toString();

}
