package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;
import java.sql.Timestamp;

public interface VOPedido {
	
	public long getIdpedido();
	public long getIdproveedor();
	public long getIdproducto() ;
	public double getVolumen() ;
	public double getPreciosubtotal() ;
	public long getSucursal();
	@Override
	public String toString();

	

}
