// third-party
import { FormattedMessage } from 'react-intl';

// assets
import { IconUser, IconUsersPlus, IconUserEdit, IconUserCog, IconUserCheck } from '@tabler/icons-react';

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
            icon: IconUserCheck,
            target: true,
            url: '/login'
        },
        {
            id: 'register',
            title: <FormattedMessage id="register" />,
            type: 'item',
            icon: IconUsersPlus,
            target: true,
            url: '/register'
        },
        {
            id: 'profile',
            title: <FormattedMessage id="profile" />,
            icon: IconUserCog,
            type: 'collapse',
            children: [
                {
                    id: 'view',
                    title: <FormattedMessage id="view-profile" />,
                    type: 'item',
                    icon: IconUser,
                    url: '/user/view-profile'
                },

                {
                    id: 'edit',
                    title: <FormattedMessage id="edit-profile" />,
                    type: 'item',
                    icon: IconUserEdit,
                    url: '/user/edit-profile'
                }
            ]
        }
    ]
};

export default other;
