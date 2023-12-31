import { lazy } from 'react';

// project imports

// import AuthGuard from 'utils/route-guard/AuthGuard';
import MainLayout from 'layout-routes/layout/MainLayout';
import Loadable from 'ui-component/Loadable';
import AuditLog from 'pages/AuditLog';
import ExternalInterfaces from 'pages/ExternalInterfaces';
import Admin from 'pages/Admin';

// sample page routing
const DashboardPage = Loadable(lazy(() => import('pages/dashboard')));
const Maps = Loadable(lazy(() => import('pages/maps/Maps')));
const Discovery = Loadable(lazy(() => import('pages/Discovery')));

//  routing
const Profile = Loadable(lazy(() => import('pages/profile/GeneralProfile')));
const NatsGeneral = Loadable(lazy(() => import('pages/nats/General')));
const NatsJetStream = Loadable(lazy(() => import('pages/nats/JetStream')));
const NatsConnections = Loadable(lazy(() => import('pages/nats/Connections')));
const NatsAccounts = Loadable(lazy(() => import('pages/nats/Accounts')));
const NatsAccountStats = Loadable(lazy(() => import('pages/nats/AccountStats')));
const NatsSubscriptions = Loadable(lazy(() => import('pages/nats/Subscriptions')));
const NatsRoutes = Loadable(lazy(() => import('pages/nats/Routes')));
const NatsLeafNodes = Loadable(lazy(() => import('pages/nats/LeafNodes')));
const NatsGateways = Loadable(lazy(() => import('pages/nats/Gateways')));
const NatsHealthProbe = Loadable(lazy(() => import('pages/nats/HealthProbe')));
const JavaDoc = Loadable(lazy(() => import('pages/JavaDoc')));
const ProtoDoc = Loadable(lazy(() => import('pages/ProtoDoc')));
const Email = Loadable(lazy(() => import('pages/template/Email')));
const Sms = Loadable(lazy(() => import('pages/template/Sms')));
const AppUserAccountProfile1 = Loadable(lazy(() => import('pages/users/Profile1')));
const AppUserAccountProfile2 = Loadable(lazy(() => import('pages/users/Profile2')));

// ==============================|| MAIN ROUTING ||============================== //

const MainRoutes = {
    path: '/',
    element: (
        <>
            <MainLayout />
        </>
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
            path: '/dashboard/maps',
            element: <Maps/>
        },
       
        {
            path: '/pages/admin',
            element: <Admin />
        },
        {
            path: '/pages/discovery',
            element: <Discovery />
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
        {
            path: '/pages/audit-log',
            element: <AuditLog />
        },
        {
            path: '/pages/external-interfaces',
            element: <ExternalInterfaces />
        }
    ]
};

export default MainRoutes;
