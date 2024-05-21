import axiosInstance from "../axiosConfig.js";

class TransbankService {
  static BASE_URL = "transbank";

  static crearTransaccion(data) {
    return axiosInstance.post(`${this.BASE_URL}/create`, data);
  }

  static confirmarTransaccion(token) {
    return axiosInstance.get(`${this.BASE_URL}/commit?token_ws=${token}`);
  }
}

export default TransbankService;