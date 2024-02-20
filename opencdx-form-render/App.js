import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { StyleSheet } from 'react-native'; // Added import statement for StyleSheet module
import LoginScreen from './screens/LoginScreen';
import HomeScreen from './screens/HomeScreen';
import ListScreen from './screens/ListScreen';
import SuccessScreen from './screens/SuccessScreen';
import TestListScreen from './screens/Flow/TestListScreen';
import OrderPlacedScreen from './screens/Flow/OrderPlacedScreen';
import TestDetailsScreen from './screens/Flow/TestDetailsScreen';
import CartScreen from './screens/Flow/CartScreen';
import PaymentMethodScreen from './screens/Flow/PaymentMethodScreen';
import InsuranceScreen from './screens/Flow/InsuranceScreen';
import AddressSection from './screens/Flow/AddressScreen';

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
              cardStyle:{
                backgroundColor:'#FFFFFF'
              }
            }}
          />
          <Stack.Screen name="List" component={ListScreen} 
            options={{
              headerShown: false,
              cardStyle:{
                backgroundColor:'#FFFFFF'
              }
            }}
          />
          <Stack.Screen name="Home" component={HomeScreen}
            options={{
              headerShown: false,
              cardStyle:{
                backgroundColor:'#FFFFFF'
              }
            }}
           />
           <Stack.Screen name="Success" component={SuccessScreen}
            options={{
              headerShown: false,
              cardStyle:{
                backgroundColor:'#FFFFFF'
              }
            }}
            />
             <Stack.Screen name="TestList" component={TestListScreen}
            options={{
              headerShown: false,
              cardStyle:{
                backgroundColor:'#FFFFFF'
              }
            }}
            />
           
            <Stack.Screen name="TestDetails" component={TestDetailsScreen}
            options={{
              headerShown: false,
              cardStyle:{
                backgroundColor:'#FFFFFF'
              }
            }}
            />
            <Stack.Screen name="Cart" component={CartScreen}
            options={{
              headerShown: false,
              cardStyle:{
                backgroundColor:'#FFFFFF'
              }
            }}
            />
            <Stack.Screen name="OrderPlaced" component={OrderPlacedScreen}
            options={{
              headerShown: false,
              cardStyle:{
                backgroundColor:'#FFFFFF'
              }
            }}
            />
            <Stack.Screen name="PaymentMethod" component={PaymentMethodScreen}
            options={{
              headerShown: false,
              cardStyle:{
                backgroundColor:'#FFFFFF'
              }
            }}
            />
            <Stack.Screen name="Insurance" component={InsuranceScreen}
            options={{
              headerShown: false,
              cardStyle:{
                backgroundColor:'#FFFFFF'
              }
            }}
            />
            <Stack.Screen name="Address" component={AddressSection}
            options={{
              headerShown: false,
              cardStyle:{
                backgroundColor:'#FFFFFF'
              }
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