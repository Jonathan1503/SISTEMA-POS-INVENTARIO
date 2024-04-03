package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.ClienteConsulta;
import uniandes.isis2304.parranderos.negocio.Pedido;


class SQLCliente {
	
	private final static String SQL = PersistenciaSuperAndes.SQL;
	
	private PersistenciaSuperAndes ps;

	public SQLCliente(PersistenciaSuperAndes ps) {
		
		this.ps = ps;
	}
	
	public long adicionarCliente (PersistenceManager pm, long id,String nombre, String correo, String tipodoc,
			long numerodoc,double puntos, String tipoCliente, String direccion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaCliente () + "(id, nombre, correo, tipodoc, numerodoc,"
        		+ " puntos, tipocliente, direccion) values (?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, nombre, correo,tipodoc,numerodoc,puntos,tipoCliente,direccion);
        return (long) q.executeUnique();
	}
	
	public Cliente darClientePorCorreo (PersistenceManager pm, String correo) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCliente () + " WHERE correo = ?");
        q.setResultClass(Cliente.class);
        q.setParameters(correo);
        return (Cliente) q.executeUnique();
    }
	
	public Cliente darClientePorId (PersistenceManager pm, long id) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCliente () + " WHERE id = ?");
        q.setResultClass(Cliente.class);
        q.setParameters(id);
        return (Cliente) q.executeUnique();
    }

	public List<ClienteConsulta> buenosClientes(PersistenceManager pm) {
		Query q = pm.newQuery(SQL, "select cliente.* ,aux.razon from cliente,((SELECT idcliente, 'Cliente frecuente' as Razon\r\n"
				+ "from venta\r\n"
				+ "group by idcliente\r\n"
				+ "having count(distinct to_number(to_char(CAST(fechaventa AS DATE),'MM')))\r\n"
				+ "     = (select count(distinct to_number(to_char(CAST(fechaventa AS DATE),'MM'))) from venta))\r\n"
				+ "UNION\r\n"
				+ "(SELECT AUX1.IDC AS IDCLIENTE,'Compra Caro' as Razon FROM(SELECT VENTA.IDCLIENTE AS IDC,COUNT(DISTINCT ID)AS NUMCOMPRAS FROM VENTA\r\n"
				+ "GROUP BY VENTA.IDCLIENTE)AUX1,(SELECT  VENTA.IDCLIENTE AS IDC,COUNT(DISTINCT VENTA.ID)AS N FROM PRODUCTO,VENTA\r\n"
				+ "WHERE PRODUCTO.PRECIOUNITARIO > 100000 AND VENTA.IDPRODUCTO= PRODUCTO.ID\r\n"
				+ "GROUP BY IDCLIENTE)AUX2\r\n"
				+ "WHERE AUX1.IDC = AUX2.IDC AND AUX1.NUMCOMPRAS = AUX2.N )\r\n"
				+ "UNION\r\n"
				+ "(SELECT AUX1.IDC AS IDCLIENTE,'Compra tecnologia/herramientas' as Razon FROM(SELECT VENTA.IDCLIENTE AS IDC,COUNT(DISTINCT ID)AS NUMCOMPRAS FROM VENTA\r\n"
				+ "GROUP BY VENTA.IDCLIENTE)AUX1,(SELECT  VENTA.IDCLIENTE AS IDC,COUNT(DISTINCT VENTA.ID)AS N FROM PRODUCTO,VENTA\r\n"
				+ "WHERE PRODUCTO.idcategoria in(10,8) AND VENTA.IDPRODUCTO= PRODUCTO.ID\r\n"
				+ "GROUP BY IDCLIENTE)AUX2\r\n"
				+ "WHERE AUX1.IDC = AUX2.IDC AND AUX1.NUMCOMPRAS = AUX2.N ))aux\r\n"
				+ "where cliente.id = aux.idcliente\r\n"
				+ "");
        q.setResultClass(ClienteConsulta.class);
        return (List<ClienteConsulta>) q.executeList();
	}
	
	

}
