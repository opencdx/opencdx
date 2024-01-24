import React, { useState } from 'react';
import { Button, TextInput, View , StyleSheet, Platform} from 'react-native';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

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
            <TextInput
                placeholder="Username"
                onChangeText={setUsername}
                style={styles.input}
                defaultValue={username}
            />
            <TextInput
                placeholder="Password"
                secureTextEntry={true}
                onChangeText={setPassword}
                defaultValue={password}
                style={[styles.input, { marginBottom: 10 }]}
            />
            <Button title="Login" onPress={handleLogin} />
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
        borderColor: 'gray',
        borderWidth: 1,
        padding: 10,
        borderRadius: 5,
        margin: 5,
    },
});

export default LoginScreen;