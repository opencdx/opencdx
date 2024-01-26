import React, { useState } from 'react';
import { View , StyleSheet, Platform, SafeAreaView} from 'react-native';
import axios from '../utils/axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Button, Heading, Input, ButtonText, InputField, Image, Link, LinkText, Text } from '@gluestack-ui/themed';

const LoginScreen = ({ navigation }) => {
    const [username, setUsername] = useState('admin@opencdx.org');
    const [password, setPassword] = useState('password');

    const handleLogin = async () => {
        try {
            const response = await axios.post('/iam/user/login', { userName: username, password: password });
            await AsyncStorage.setItem('jwtToken', response.data.token);
            navigation.navigate('List');
        } catch (error) {
            alert('Invalid credentials');
        }
    };

    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.body}>
            <Image
                size="md"
                resizeMode="contain"
                alt="OpenCDX logo"
                style={styles.image}
                source={require('../assets/Open.png')}
            />
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
            <Link href="" style={styles.forget}>
                <LinkText>Forget Password?</LinkText>
            </Link>
            </View>
            <View style={styles.footer}>
                <Button title="Sign In" onPress={handleLogin} style={styles.button}>
                    <ButtonText>Sign In</ButtonText>
                </Button>
                <Link href="" style={styles.signup}>
                    <LinkText>Don't have an account? Sign Up</LinkText>
                </Link>
            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
      flex: 1,
      shadowColor: "#000",
      margin: 'auto',
      ...Platform.select({
        web: {
            minWidth: 500,
            justifyContent: 'center',
        },
        default: {
            justifyContent: 'space-between',
        }
    })
    },
    input: {
        marginBottom: 10,
    },
    button: {
        marginBottom: 10,
    },
    image: {
        marginBottom: 5,
        justifyContent: 'center',
        width: 500,
    },
    forget: {
        marginTop: 10,
        textAlign: 'right',
        flexDirection: "row",
        justifyContent: "flex-end",
    },
    signup: {
        marginBottom: 10,
        textAlign: 'right',
        flexDirection: "row",
        justifyContent: "center",
    },
    body: {
        padding: 8,
        ...Platform.select({
            web: {

            },
            default: {
                flex: 1,
                justifyContent: 'center',
            }
        })
    },
    footer: {
        padding: 8,
    },
});

export default LoginScreen;