import React, { useState } from 'react';
import { View , StyleSheet, Platform} from 'react-native';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Button, Heading, Input, ButtonText, InputField } from '@gluestack-ui/themed';

const LoginScreen = ({ navigation }) => {
    const [username, setUsername] = useState('admin@opencdx.org');
    const [password, setPassword] = useState('password');

    const handleLogin = async () => {
        try {
            const response = await axios.post('https://localhost:8080/iam/user/login', { userName: username, password: password });
            await AsyncStorage.setItem('jwtToken', response.data.token);
            navigation.navigate('List');
        } catch (error) {
            alert('Invalid credentials');
        }
    };

    return (
        <View style={styles.container}>
            <Heading>Sign In</Heading>
            <Input
                onChangeText={setUsername}
                style={styles.input}
            >
                <InputField placeholder="Username" defaultValue={username}/>
            </Input>
            <Input
                onChangeText={setPassword}
                style={styles.input}
            >
                <InputField type="password" placeholder="Password" defaultValue={password}/>
            </Input>
            <Button title="Login" onPress={handleLogin}>
                <ButtonText>Login</ButtonText>
            </Button>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
      flex: 1,
      justifyContent: 'center',
      padding: 8,
      shadowColor: "#000",
      margin: 'auto',
      ...Platform.select({
        web: {
            minWidth: 500,
        },
        default: {
            
        }
    })
    },
    input: {
        marginBottom: 10,
    },
});

export default LoginScreen;