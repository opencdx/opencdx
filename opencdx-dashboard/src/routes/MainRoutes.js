import { lazy } from 'react';

// project imports
import GuestGuard from 'utils/route-guard/GuestGuard';

//import AuthGuard from 'utils/route-guard/AuthGuard';
import MainLayout from 'layout/MainLayout';
import Loadable from 'ui-component/Loadable';

// sample page routing
const DashboardPage = Loadable(lazy(() => import('views/dashboard')));

// ==============================|| MAIN ROUTING ||============================== //

const MainRoutes = {
    path: '/',
    element: (
        <GuestGuard>
            <MainLayout />
        </GuestGuard>
    ),
    children: [
        {
            path: '/',
            element: <DashboardPage />
        },
        {
            path: '/dashboard',
            element: <DashboardPage />
        }
    ]
};

export default MainRoutes;
