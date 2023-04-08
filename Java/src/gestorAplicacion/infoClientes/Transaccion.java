/* Clase Transcacion
 *
 * Es una clase que sólamente tiene atributos de tal forma que, en conjunto, permiten reconstruir a detalle la información sobre
 * una operación bancaria.
 *
 */

package gestorAplicacion.infoClientes;

import gestorAplicacion.productosFinancieros.Corriente;
import gestorAplicacion.productosFinancieros.Cuenta;
import java.io.Serializable;
import java.util.ArrayList;

public class Transaccion implements Serializable {
	private Cuenta cuentaOrigen;
	private Cuenta cuentaDestino;
	private String nombreBanco;
	private float valorTransaccion;
	
    // El Array de clase de clientes de encarga de guardar todas las instancias de
    // Cliente para poder guardar y cargarlas en la serializacion
	private static ArrayList<Transaccion> transaccion = new ArrayList<>();
	
    // Constructor para transferencias entre cuentas
	public Transaccion(Cuenta cuentaOrigen,Cuenta cuentaDestino,float valorTransaccion) {
		this.cuentaOrigen = cuentaOrigen;
		this.cuentaDestino = cuentaDestino;
		this.valorTransaccion = valorTransaccion;	
	}
	
    // Constructor para pagos de créditos
	public Transaccion(Cuenta cuentaOrigen,String nombreBanco,float valorTransaccion) {
		this.cuentaOrigen = cuentaOrigen;
		this.nombreBanco = nombreBanco;
		this.valorTransaccion = valorTransaccion;	
	}

    // Formateo del texto al imprimirse un objeto de tipo transacción
	public String toString() {
		
		if (this.cuentaDestino == null) {

			return "Transferencia de"+ cuentaOrigen.getTitular().getCc() + " desde la cuenta " + cuentaOrigen.getNroCuenta() + " a " + getNombreBanco() + " por valor de " + getValorTransaccion() + "." ;

		}else {

			return "Transferencia de " + cuentaOrigen.getTitular().getCc() + " desde la cuenta " + getCuentaOrigen().getNroCuenta() + " al usuario con CC: " + cuentaDestino.getTitular().getCc() + " con cuenta " + cuentaDestino.getNroCuenta() + " por valor de " + getValorTransaccion() + ".";

		}
	}

//	-------------------------------------- Metodos get-set --------------------------------------
    public static ArrayList<Transaccion> getTransaccion() { return transaccion; }

    public static void setTransaccion(ArrayList<Transaccion> transaccion) { Transaccion.transaccion = transaccion; }
	
	public void setCuentaOrigen(Cuenta cuentaOrigen) { this.cuentaOrigen = cuentaOrigen; }

	public Cuenta getCuentaOrigen() { return this.cuentaOrigen;	}
	
	public void setCuentaDestino(Cuenta cuentaDestino) { this.cuentaDestino = cuentaDestino; }

	public Cuenta getCuentaDestino() { return this.cuentaDestino; }
	
	public void setNombreBanco(String nombreBanco) { this.nombreBanco = nombreBanco; }

	public String getNombreBanco() { return this.nombreBanco; }
	
	public void setValorTransaccion(float valorTransaccion) { this.valorTransaccion = valorTransaccion;	}

	public float getValorTransaccion() { return this.valorTransaccion; }
	//--------------------------------------------------------------------------------------
	
	
}
