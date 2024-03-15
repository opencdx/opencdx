import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { StyleSheet } from 'react-native'; // Added import statement for StyleSheet module
import LoginScreen from './screens/LoginScreen';
import User from './screens/User/User';
import ListQuestion from './screens/User/ListQuestion';
import ListScreen from './screens/ListScreen';
import SuccessScreen from './screens/SuccessScreen';
import TestListScreen from './screens/TakeTest/Flow/TestListScreen';
import OrderPlacedScreen from './screens/TakeTest/Flow/OrderPlacedScreen';
import TestDetailsScreen from './screens/TakeTest/Flow/TestDetailsScreen';
import CartScreen from './screens/TakeTest/Flow/CartScreen';
import PaymentMethodScreen from './screens/TakeTest/Flow/PaymentMethodScreen';
import InsuranceScreen from './screens/TakeTest/Flow/InsuranceScreen';
import AddressSection from './screens/TakeTest/Flow/AddressScreen';

import { GluestackUIProvider} from '@gluestack-ui/themed';
import { config } from '@gluestack-ui/config';
import StartVirtualConsult from './screens/VirtualConsult/StartVirtualConsult';
import CheckVirtualConsult from './screens/VirtualConsult/CheckVirtualConsult';
import ConfirmVirtualConsult from './screens/VirtualConsult/ConfirmVirtualConsult';
import PriorVirtualConsult from './screens/VirtualConsult/PriorVirtualConsult';
import SlideVirtualConsult from './screens/VirtualConsult/SlideVirtualConsult';
import GetTested from './screens/TakeTest/GetTested';
import ScheduleAppointment from './screens/TakeTest/ScheduleAppoinment';
import TestKit from './screens/TakeTest/TestKit';
import TestHistory from './screens/TestHistory/TestHistory';
import VaccineHistory from './screens/VaccineHistory/VaccineHistory';
import TestRecord from './screens/VaccineHistory/TestRecord';

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
          <Stack.Screen name="User" component={User}
            options={{
              headerShown: false,
              cardStyle: {
                backgroundColor: '#FFFFFF'
              }
            }}
          />
          <Stack.Screen name="ListQuestion" component={ListQuestion}
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
          <Stack.Screen name="StartVirtualConsult" component={StartVirtualConsult}
            options={{
              cardStyle:{
                backgroundColor:'#FFFFFF'
              },
              headerTitle: 'Virtual Consult',
            }}
            />
          <Stack.Screen name="CheckVirtualConsult" component={CheckVirtualConsult}
            options={{
              cardStyle:{
                backgroundColor:'#FFFFFF'
              },
              headerTitle: 'Virtual Consult',
            }}
            />
          <Stack.Screen name="ConfirmVirtualConsult" component={ConfirmVirtualConsult}
            options={{
              cardStyle:{
                backgroundColor:'#FFFFFF'
              },
              headerTitle: 'Virtual Consult',
            }}
            />
          <Stack.Screen name="PriorVirtualConsult" component={PriorVirtualConsult}
            options={{
              cardStyle:{
                backgroundColor:'#FFFFFF'
              },
              headerTitle: 'Virtual Consult',
            }}
            />

          <Stack.Screen name="SlideVirtualConsult" component={SlideVirtualConsult}
            options={{
              cardStyle:{
                backgroundColor:'#FFFFFF'
              },
              headerTitle: 'Virtual Consult',
            }}
            />
          <Stack.Screen name="GetTested" component={GetTested}
            options={{
              cardStyle:{
                backgroundColor:'#FFFFFF'
              },
              headerTitle: 'Get Tested',
            }}
            />
          <Stack.Screen name="ScheduleAppointment" component={ScheduleAppointment}
            options={{
              cardStyle:{
                backgroundColor:'#FFFFFF'
              },
              headerTitle: 'Schedule Appointment',
            }}
            />
          <Stack.Screen name="TestKit" component={TestKit}
            options={{
              cardStyle:{
                backgroundColor:'#FFFFFF'
              },
              headerTitle: 'Register Test',
            }}
            />
          <Stack.Screen name="TestHistory" component={TestHistory}
            options={{
              cardStyle:{
                backgroundColor:'#FFFFFF'
              },
              headerTitle: 'Test History',
            }}
            />
            <Stack.Screen name="TestRecord" component={TestRecord}
            options={{
              cardStyle:{
                backgroundColor:'#FFFFFF'
              },
              headerTitle: 'Test Record',
            }}
            />
          <Stack.Screen name="VaccineHistory" component={VaccineHistory}
            options={{
              cardStyle:{
                backgroundColor:'#FFFFFF'
              },
              headerTitle: 'Vaccine History',
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