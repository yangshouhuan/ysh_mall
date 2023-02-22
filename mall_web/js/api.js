function requestGET(data) {
    return request({
        url: data.url,
        method: 'get',
        params: data.data,
        headers: data.headers || {}
    }) 
}

function requestPOST(url, data) {
    return request({
        url: data.url,
        method: 'post',
        data: data.data,
        headers: data.headers || {}
    })
}

function requestPUT(url, data) {
    return request({
        url: data.url,
        method: 'put',
        data: data.data,
        headers: data.headers || {}
    })
}