import { lazy } from 'react';

// project imports
import Loadable from 'ui-component/Loadable';
// import AuthGuard from 'utils/route-guard/AuthGuard';
import MinimalLayout from 'layout-routes/layout/MinimalLayout';

// login routing
const AuthLogin = Loadable(lazy(() => import('pages/authentication/Login3')));
const AuthRegister = Loadable(lazy(() => import('pages/authentication/Register3')));

// ==============================|| AUTHENTICATION ROUTING ||============================== //

const AuthenticationRoutes = {
    path: '/',
    element: <MinimalLayout />,
    children: [
        {
            path: '/login',
            element: <AuthLogin />
        },
        {
            path: '/register',
            element: <AuthRegister />
        }
    ]
};

export default AuthenticationRoutes;
