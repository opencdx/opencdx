import { useContext } from 'react';
import { ConfigContext } from 'utils/contexts/ConfigContext';

// ==============================|| CONFIG - HOOKS ||============================== //

const useConfig = () => useContext(ConfigContext);

export default useConfig;
