export const PORT = 8632;
export const environment = {
    development: {
        serverURL: `http://localhost:${PORT}/`,
        dbString: 'mongodb://appuser:appuser@database:27017/opencdx'
    },
    production: {
        serverURL: `http://localhost:${PORT}/`,
        dbString: 'mongodb://appuser:appuser@database:27017/opencdx'
    }
}