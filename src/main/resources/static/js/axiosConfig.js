const axiosInstance = axios.create({
  baseURL: "http://localhost:8080/api/v1/"
});

axiosInstance.interceptors.request.use(config => {
  // Obtiene el token desde el localStorage
  const token = localStorage.getItem("token") || "";
  if (!token) return config;

  // Ingresa el token en el encabezado de las solicitudes que se hagan
  config.headers['Authorization'] = 'Bearer ' + token;
  return config;
});

axiosInstance.interceptors.response.use(response => response, error => {
  const originalRequest = error.config;
  // Elimina el token y el usuario almacenado del localStorage y resetea el encabezado en caso de que el token este expirado o sea invalido
  // Luego vuelve a ejecutar la solicitud
  if (error.response.status === 401 && !originalRequest._retry && localStorage.getItem("token")) {
    originalRequest._retry = true;
    localStorage.removeItem("token");
    localStorage.removeItem("usuario");

    originalRequest.headers['Authorization'] = "";
    return axiosInstance(originalRequest);
  }
  return Promise.reject(error.response);
});

export default axiosInstance;