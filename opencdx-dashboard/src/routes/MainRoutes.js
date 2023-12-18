import { lazy } from 'react';

// project imports

import AuthGuard from 'utils/route-guard/AuthGuard';
import MainLayout from 'layout/MainLayout';
import Loadable from 'ui-component/Loadable';

// sample page routing
const DashboardPage = Loadable(lazy(() => import('views/dashboard')));

//  routing
const Profile = Loadable(lazy(() => import('views/pages/profile/GeneralProfile')));
const NatsGeneral = Loadable(lazy(() => import('views/pages/nats/General')));
const NatsJetStream = Loadable(lazy(() => import('views/pages/nats/JetStream')));
const NatsConnections = Loadable(lazy(() => import('views/pages/nats/Connections')));
const NatsAccounts = Loadable(lazy(() => import('views/pages/nats/Accounts')));
const NatsAccountStats = Loadable(lazy(() => import('views/pages/nats/AccountStats')));
const NatsSubscriptions = Loadable(lazy(() => import('views/pages/nats/Subscriptions')));
const NatsRoutes = Loadable(lazy(() => import('views/pages/nats/Routes')));
const NatsLeafNodes = Loadable(lazy(() => import('views/pages/nats/LeafNodes')));
const NatsGateways = Loadable(lazy(() => import('views/pages/nats/Gateways')));
const NatsHealthProbe = Loadable(lazy(() => import('views/pages/nats/HealthProbe')));
const JavaDoc = Loadable(lazy(() => import('views/pages/JavaDoc')));
const ProtoDoc = Loadable(lazy(() => import('views/pages/ProtoDoc')));
const Email = Loadable(lazy(() => import('views/pages/template/Email')));
const Sms = Loadable(lazy(() => import('views/pages/template/Sms')));
const AppUserAccountProfile1 = Loadable(lazy(() => import('views/pages/users/Profile1')));
const AppUserAccountProfile2 = Loadable(lazy(() => import('views/pages/users/Profile2')));

// ==============================|| MAIN ROUTING ||============================== //

const MainRoutes = {
    path: '/',
    element: (
        <AuthGuard>
            <MainLayout />
        </AuthGuard>
    ),
    children: [
        {
            path: '/',
            element: <DashboardPage />
        },
        {
            path: '/dashboard',
            element: <DashboardPage />
        },
        {
            path: '/pages/general',
            element: <NatsGeneral />
        },
        {
            path: '/pages/jet',
            element: <NatsJetStream />
        },
        {
            path: '/pages/connections',
            element: <NatsConnections />
        },
        {
            path: '/pages/accounts',
            element: <NatsAccounts />
        },
        {
            path: '/pages/accounts-stats',
            element: <NatsAccountStats />
        },
        {
            path: '/pages/subscriptions',
            element: <NatsSubscriptions />
        },
        {
            path: '/pages/routes',
            element: <NatsRoutes />
        },
        {
            path: '/pages/leaf-nodes',
            element: <NatsLeafNodes />
        },
        {
            path: '/pages/gateways',
            element: <NatsGateways />
        },
        {
            path: '/pages/health-probe',
            element: <NatsHealthProbe />
        },
        {
            path: '/pages/proto',
            element: <ProtoDoc />
        },
        {
            path: '/pages/java',
            element: <JavaDoc />
        },
        {
            path: '/pages/email',
            element: <Email />
        },
        {
            path: '/pages/sms',
            element: <Sms />
        },
        {
            path: '/pages/profile',
            element: <Profile />
        },
        {
            path: '/user/view-profile',
            element: <AppUserAccountProfile1 />
        },
        {
            path: '/user/edit-profile',
            element: <AppUserAccountProfile2 />
        },
    ]
};

export default MainRoutes;
