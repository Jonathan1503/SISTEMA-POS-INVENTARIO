package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;
import java.sql.Timestamp;

public interface VOVenta {
	
	public long getId();
	public long getIdCliente();
	public long getIdProducto();
	public long getCantidad();
	public  Timestamp getFechaVenta();
	public double getTotal();
	public long getSucursal();
	@Override
	public String toString();

}
