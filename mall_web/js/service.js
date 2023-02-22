
// 创建axios实例
const request = axios.create({
  baseURL: 'http://localhost:8090/', // api的base_url
  timeout: 300000, // 请求超时时间
  // withCredentials: true   // 设置请求携带cookie  保证session有效性
})

// request拦截器
request.interceptors.request.use(config => {
  const token = document.cookie.split('=')[1] || null
  if (token) {
    config.headers['token'] = token
  }
	return config
}, error => {
  Promise.reject(error)
})

// respone拦截器
request.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    return Promise.reject(error)
  }
)