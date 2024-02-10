import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { StyleSheet} from 'react-native';
import LoginScreen from './screens/LoginScreen';
import HomeScreen from './screens/HomeScreen';
import ListScreen from './screens/ListScreen';

import { GluestackUIProvider} from '@gluestack-ui/themed';
import { config } from '@gluestack-ui/config';

const Stack = createStackNavigator();

const App = () => {
  return (
    <GluestackUIProvider config={config}>
      <NavigationContainer styles={styles.container}>
        <Stack.Navigator initialRouteName="Login">
          <Stack.Screen name="Login" component={LoginScreen} 
            options={{
              headerShown: false,
            }}
          />
          <Stack.Screen name="List" component={ListScreen} 
            options={{
              headerShown: false,
            }}
          />
          <Stack.Screen name="Home" component={HomeScreen}
            options={{
              headerShown: false,
            }}
           />
        </Stack.Navigator>
      </NavigationContainer>
    </GluestackUIProvider>
  );
};

const styles = StyleSheet.create({
  container: {
    justifyContent: 'center',
    backgroundColor: '#fff',
  },
});

export default App;