package uniandes.isis2304.parranderos.negocio;

public class resultadosVenta {
	private long sucursal;
	private double total;
	
	public resultadosVenta(long sucursal, double total) {
		
		this.sucursal = sucursal;
		this.total = total;
	}

	public long getSucursal() {
		return sucursal;
	}

	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "resultadosVenta [sucursal=" + sucursal + ", total=" + total + "]";
	}
	
	
	

}
