import axiosInstance from "../axiosConfig.js";

class AuthService {
  static BASE_URL = "auth";

  static login(credentials) {
    return axiosInstance.post(`${this.BASE_URL}/login`, credentials).then(res => {
      // Obtiene el token de la API y lo ingresa al localStorage
      const { token } = res.data;
      localStorage.setItem("token", token);

      // Ingresa el token obtenido en el encabezado las solicitudes que se hagan
      axiosInstance.interceptors.request.use(config => {
        config.headers['Authorization'] = 'Bearer ' + token;
        return config;
      });

      // Obtiene los datos del usuario y los retorna
      return this.getUserData();
    });
  }

  static register(formData) {
    return axiosInstance.post(`${this.BASE_URL}/register`, formData).then(res => {
      // Obtiene el token de la API y lo ingresa al localStorage
      const { token } = res.data;
      localStorage.setItem("token", token);

      // Ingresa el token obtenido en el encabezado las solicitudes que se hagan
      axiosInstance.interceptors.request.use(config => {
        config.headers['Authorization'] = 'Bearer ' + token;
        return config;
      });

      // Obtiene los datos del usuario y los retorna
      return this.getUserData();
    });
  }

  static getUserData() {
    return axiosInstance.get(`${this.BASE_URL}/profile`).then(({ data }) => data);
  }
}

export default AuthService;