// third-party
import { FormattedMessage } from 'react-intl';

// assets

import { SupervisedUserCircle, VerifiedUser, Login, LoginSharp } from '@mui/icons-material';

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
            icon: LoginSharp,
            target: true,
            url: '/register'
        },
        {
            id: 'profile',
            title: <FormattedMessage id="profile" />,
            icon: SupervisedUserCircle,
            type: 'collapse',
            children: [
                {
                    id: 'view',
                    title: <FormattedMessage id="view-profile" />,
                    type: 'item',
                    icon: VerifiedUser,
                    url: '/user/view-profile'
                },

                {
                    id: 'edit',
                    title: <FormattedMessage id="edit-profile" />,
                    type: 'item',
                    icon: VerifiedUser,
                    url: '/user/edit-profile'
                }
            ]
        }
    ]
};

export default other;
