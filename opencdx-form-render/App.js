import { StyleSheet, View } from 'react-native';
import Constants from 'expo-constants';
import FormRender from './components/FormRender';

export default function App() {

  return (
    <View style={styles.container}>
      <FormRender/>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    paddingTop: Constants.statusBarHeight,
    padding: 8,
    maxWidth: 500,
  },
});
