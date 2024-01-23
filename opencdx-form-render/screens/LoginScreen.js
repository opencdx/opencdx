import React, { useState } from 'react';
import { Button, TextInput, View , StyleSheet} from 'react-native';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

const LoginScreen = ({ navigation }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async () => {
        try {
            const response = await axios.post('https://localhost:8080/iam/user/login', { userName: 'admin@opencdx.org', password: 'password' });

            await AsyncStorage.setItem('jwtToken', response.data.token);
            navigation.navigate('Home');
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
            />
            <TextInput
                placeholder="Password"
                secureTextEntry={true}
                onChangeText={setPassword}
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
      borderColor: 'white',
      borderWidth: 1,
      shadowColor: "#000",
    },
    input: {
        borderColor: 'gray',
        borderWidth: 1,
        padding: 10,
        borderRadius: 5,
    },
});

export default LoginScreen;