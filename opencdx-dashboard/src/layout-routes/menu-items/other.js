// third-party
import { FormattedMessage } from 'react-intl';

// assets

import { Login, PersonAdd, AccountCircle, PersonSearch, ManageAccounts } from '@mui/icons-material';
// ==============================|| SAMPLE PAGE & DOCUMENTATION MENU ITEMS ||============================== //
const other = {
    id: 'other',
    title: <FormattedMessage id="profile" />,
    type: 'group',
    children: [
        {
            id: 'login',
            title: <FormattedMessage id="login" />,
            type: 'item',
            icon: Login,
            target: true,
            url: '/login'
        },
        {
            id: 'register',
            title: <FormattedMessage id="register" />,
            type: 'item',
            icon: PersonAdd,
            target: true,
            url: '/register'
        },
        {
            id: 'profile',
            title: <FormattedMessage id="profile" />,
            icon: AccountCircle,
            type: 'collapse',
            children: [
                {
                    id: 'view',
                    title: <FormattedMessage id="view-profile" />,
                    type: 'item',
                    icon: PersonSearch,
                    url: '/user/view-profile'
                },

                {
                    id: 'edit',
                    title: <FormattedMessage id="edit-profile" />,
                    type: 'item',
                    icon: ManageAccounts,
                    url: '/user/edit-profile'
                }
            ]
        }
    ]
};

export default other;
