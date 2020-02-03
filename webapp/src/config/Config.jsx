const dev = {
    api: {
        baseURL: 'http://localhost:8080/api',
    },
    apiWebSocket: {
        baseURL: 'ws://localhost:8080/api',
    }
};

const prod = {
    api: {
        baseURL: 'https://api.amrane.fr/',
    },
    apiWebSocket: {
        baseURL: 'ws://api.amrane.fr/',
    }
};

const config = process.env.NODE_ENV === 'production' ? prod : dev;

export default {
    ...config
};
