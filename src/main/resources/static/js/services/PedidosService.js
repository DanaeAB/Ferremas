import axiosInstance from "../axiosConfig.js";

class PedidosService {
  static BASE_URL = "pedidos";

  static crearPedido(data) {
    return axiosInstance.post(this.BASE_URL, data);
  }
  static obtenerPedidos() {
    return axiosInstance.get(this.BASE_URL);
  }
  static obtenerPedido(id) {
    return axiosInstance.get(`${this.BASE_URL}/${id}`);
  }
}

export default PedidosService;